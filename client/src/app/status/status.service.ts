import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';

import 'rxjs/add/operator/publishReplay';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';

import { environment } from '../../environments/environment';


@Injectable()
export class StatusService {

  constructor(private http: Http) { }

  getStatuses(): Observable<any> {
      return this.http.get(environment.serverUrl + 'status').map((res: Response) => res.json());
  }
}
