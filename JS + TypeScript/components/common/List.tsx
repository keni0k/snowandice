import * as React from 'react';

import { default as MuiList } from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';

type ListProps<T> = {
  items: any[] | Map<string, any>,
  className?: string,
  renderItem: (item: T) => JSX.Element | string
};
type ListState = {};

export default class List<T> extends React.Component<ListProps<T>, ListState> {

  constructor(props: ListProps<T>) {
    super(props);
    this.state = {
    };
  }

  public render() {
    const {
      items,
      className,
      renderItem,
    }: ListProps<T> = this.props;

    // todo: use reselect
    const array = items instanceof Array
      ? items
      : (Array.from(items) as any[][]).reduce( (r, _) => { r.push(_[1]); return r}, []);
    
    return (
      <div className={className}>
        <MuiList>
          {array.map( (item,  index) => {
            return(
              <ListItem
                key={index}
              >
                {renderItem(item)}
              </ListItem>
            )
          })}
        </MuiList>
      </div>
    );
  }
}