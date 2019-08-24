import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  private errorAlert: boolean;
  private error_message: string;
  apiUrl: string = 'http://localhost:8080/api/parkings';
  private vehicleForm: FormGroup;
  private parkings = [];

  constructor(private http: HttpClient, private formBuilder: FormBuilder){}

  ngOnInit(): void {
    this.getAllParking();
    this.vehicleForm = this.formBuilder.group({
      vehicleLot: ['', Validators.required],
      vehicleNumber: ['', Validators.required],
      vehicleDuration:  ['', Validators.required],
      vehicleAmount:  ['', Validators.required]
    });
  }

  getData() {
    return this.http.get(this.apiUrl, {
      reportProgress: true
    });
  }
  getAllParking(){
    this.getData().subscribe((results : any[])=>{
      console.log(results);
      this.parkings = results;
      });
  }

  onSubmit() {
    if (this.vehicleForm.valid) {
      this.parkNewvehicle(this.getCustomVehicleForm());
    }
  }

  parkNewvehicle(vehicle: FormGroup) {
    return this.http.post(this.apiUrl,vehicle.value).subscribe(res=>{
        this.errorAlert = false;
        this.parkings.push(res); 
    },err => {
      this.errorAlert = true;
      this.error_message = err.error.message;
    });
  }

  getCustomVehicleForm(){
    return new FormGroup({
        lot: this.vehicleForm.get('vehicleLot'),
        parkingAmount: this.vehicleForm.get('vehicleAmount'),
        parkingDuration: this.vehicleForm.get('vehicleDuration'),
        vehicleNumber: this.vehicleForm.get('vehicleNumber')
    });
  }

  calculateAmount(boxInput : HTMLInputElement){
    let vehicleDuration = this.vehicleForm.get('vehicleDuration').value; //this will have the length of the text entered in the input box
    let calAmount = 20;
    if(vehicleDuration>60){
      calAmount += (vehicleDuration - 60)*0.333;
    }
    this.vehicleForm.get('vehicleAmount').setValue(calAmount);
  }


}
