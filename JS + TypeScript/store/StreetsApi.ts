import { Api } from "../common/Api";

export class StreetsApi extends Api{
  fetchRoutes = (count: number) => this.get<any>('/api/routes', {machinesCount: count});
}
