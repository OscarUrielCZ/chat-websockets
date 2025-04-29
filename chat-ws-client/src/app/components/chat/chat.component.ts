import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import * as Stomp from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Message } from '../../models/message';

const url: string = "http://localhost:8090";
const endpoint: string = "/chat-websocket";

@Component({
  selector: 'app-chat',
  imports: [FormsModule, CommonModule],
  templateUrl: './chat.component.html',
})
export class ChatComponent {
  client!: Stomp.Client;
  connected: boolean = false;

  userId: string = "";
  writing: string = "";
  message: Message = new Message();
  messageHistory: Message[] = [];

  constructor() {
    this.userId = "id-" + new Date().getTime() + "-" + Math.random().toString(16).substring(2);
    console.log("holaaa " + this.userId);
  }

  ngOnInit(): void {
    this.client = new Stomp.Client({
      brokerURL: undefined,
      webSocketFactory: () => new SockJS(url + endpoint),
      debug: str => console.log(str),
      reconnectDelay: 5000
    });

    this.client.onConnect = (frame) => {
      this.connected = true;
      console.log(`Conectados : ${this.client.connected} : ${frame}`);

      this.client.subscribe("/roomA/message", (e) => {
        if (e.body) {
          const newMessage: Message = JSON.parse(e.body) as Message;

          if (newMessage.type === "join" && newMessage.username === "@" + this.message.username) {
            this.message.textColor = newMessage.textColor;
          } else if (newMessage.type === "writing" && newMessage.username !== this.message.username) {
            this.writing = newMessage.content;
            setTimeout(() => this.writing = "", 2000);
          }

          if (newMessage.type !== "writing") {
            newMessage.date = new Date(newMessage.date);
            this.messageHistory.push(newMessage);
          }
        }
      });

      this.client.subscribe(`/roomA/history/${this.userId}`, (e) => {
        if (e.body) {
          const history: Message[] = JSON.parse(e.body) as Message[];
          this.messageHistory = history;
        }
      });

      // request messages history
      this.client.publish({
        destination: "/app/get-history",
        body: this.userId
      });

      // notify to group I joined
      this.client.publish({
        destination: "/app/message",
        body: JSON.stringify({...this.message, type: "join" })
      });

    };

    this.client.onDisconnect = (frame) => {
      this.connected = false;
      console.log(`Desconectados : ${this.client.connected} : ${frame}`);
    };
  }

  connect(): void {
    this.client.activate();
  }

  disconnect(): void {
    this.client.deactivate();
    this.messageHistory = [];
    this.message = new Message();
  }

  sendMessage() {
    this.message.type = "message";
    this.client.publish({
      destination: "/app/message",
      body: JSON.stringify(this.message)
    })
    this.message.content = "";
  };

  writingMessage() {
    this.client.publish({
      destination: "/app/writing",
      body: this.message.username
    });
  }
}
