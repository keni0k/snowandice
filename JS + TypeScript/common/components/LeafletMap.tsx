import * as React from 'react';
import * as L from "leaflet"
import { Point } from "leaflet";

let DefaultIcon = L.icon({
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
  iconAnchor: new Point(12.5, 41, false)
});

L.Marker.prototype.options.icon = DefaultIcon;

type LeafletMapProps = {
  height?: string|number,
  width?: string|number,
}

export default class LeafletMap extends React.PureComponent<LeafletMapProps, {}> {
  map: L.Map | null = null;
  mapContainer: HTMLDivElement | null = null;

  componentDidMount(){
    if (!this.mapContainer) return;
    const map = this.map = L.map(this.mapContainer, {
      minZoom: 2,
      maxZoom: 20,
      layers: [
        L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png')
      ],
      attributionControl: false,
    });

    map.fitWorld();
  }

  componentWillUnmount() {
    this.map = null;
  }

  render(){
    return (
      <div ref={c => this.mapContainer = c } style={{ height: this.props.height, width: this.props.width}}/>
    )
  }
}
