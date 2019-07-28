export interface ISize {
  height: number, 
  width: number
}

export interface IDisplayService {
  getDisplaySize: () => ISize,
  getContentSize: () => ISize
}

export class DisplayService implements IDisplayService {
  constructor(props: {verticalOffset: number}){
    this.verticalOffset = props.verticalOffset
  }
  verticalOffset: number;
  getContentSize = () => ({ height: window.innerHeight, width: window.innerWidth });
  getDisplaySize = () => ({ height: window.innerHeight - this.verticalOffset, width: window.innerWidth });
}