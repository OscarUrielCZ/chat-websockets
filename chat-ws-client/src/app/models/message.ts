export class Message {
  date!: Date;
  type!: "message" | "join" | "leave" | "writing";
  content: string = "";
  username!: string;
  textColor: string = "black";
}
