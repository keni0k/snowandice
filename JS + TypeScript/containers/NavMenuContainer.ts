import { connect } from "react-redux";
import { RootState } from "../store";
import * as ApplicationHeaderStore from "../store/ApplicationHeader";
import * as ApplicationStore from "../store/Application";
import NavMenu from "../components/NavMenu";

export default connect(
  (state: RootState) => ({
    ...state.applicationHeader,
    ...state.application
  }),
  {
    ...ApplicationHeaderStore.actionCreators,
    ...ApplicationStore.actionCreators
  }
)(NavMenu);
