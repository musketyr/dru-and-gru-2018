import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {IndexComponent} from "./index/index.component";
import {StatusComponent} from "./status/status.component";

const routes: Routes = [
  {path: '', redirectTo: 'statuses', pathMatch: 'full'},
  {path: 'index', component: IndexComponent},
  {path: 'statuses', component: StatusComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
