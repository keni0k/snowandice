import * as React from 'react';
import { RouteComponentProps } from "react-router";
import { connect } from 'react-redux';

import { ActionCreatorsMapObject, RootState } from '../store';
import * as ApplicationStore from '../store/Application';
import { push } from "react-router-redux";

type RootPageActions = { push: typeof push } & typeof ApplicationStore.actionCreators

type RootPageDispatchProps = ActionCreatorsMapObject<RootPageActions>

type RootPageProps = {
    skipRedirect: boolean
    children: JSX.Element
  }
  & RootPageDispatchProps
  & RouteComponentProps<{}>;

type RootPageState = {};

class RootPage extends React.Component<RootPageProps, RootPageState> {

  constructor(props: any) {
    super(props);
  }

  componentDidMount() {

  }

  redirect() {

  }

  public render() {
    const {
      children
    }: RootPageProps = this.props;

    return (
      <div className="flex-container flex-container--vertical fill-parent">
        {children}
      </div>
    );
  }
}

export const RootContainer = connect(
  (state: RootState) => ({
  }),
  {
    ...ApplicationStore.actionCreators,
    push
  }
)(RootPage);

export function wrap(Component: React.ComponentType, skipRedirect = true) {
  return class extends React.Component {
    static displayName = `${Component.displayName || Component.name || ''} with Root`;

    render() {
      return <RootContainer skipRedirect={true}><Component {...this.props} /></RootContainer>;
    }
  };
}
