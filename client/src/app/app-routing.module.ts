import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {IndexComponent} from "./index/index.component";
import {StatusComponent} from "./status/status.component";

const routes: Routes = [
  {path: '', redirectTo: 'states', pathMatch: 'full'},
  {path: 'index', component: IndexComponent},
  {path: 'states', component: StatusComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
