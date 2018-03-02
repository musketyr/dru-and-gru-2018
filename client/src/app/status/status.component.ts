import {Component} from '@angular/core';
import {StatusService} from './status.service';
import {OnInit} from '@angular/core';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {

  states: any;

  constructor(private statusService: StatusService) { }

  ngOnInit(): void {
    this.statusService.getStates().subscribe(res => this.states = res);
  }
}
