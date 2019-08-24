import { TestBed, async, inject } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { HttpEvent, HttpEventType } from '@angular/common/http';

import {
  HttpTestingController
} from '@angular/common/http/testing';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ]
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should get parking Lot',
    async(inject(
      [HttpTestingController, AppComponent],
      (httpMock: HttpTestingController,dataService: AppComponent) => {
        const mockUsers = [
          [
            {
                "id": 1,
                "lot": 1,
                "createdAt": "2019-08-21T02:16:31.000+0000",
                "parkingAmount": 20,
                "parkingDuration": 6,
                "updatedAt": "2019-08-21T02:16:31.000+0000",
                "vehicleNumber": 2
            },
            {
                "id": 2,
                "lot": 2,
                "createdAt": "2019-08-21T02:18:33.000+0000",
                "parkingAmount": 69,
                "parkingDuration": 210,
                "updatedAt": "2019-08-21T02:18:33.000+0000",
                "vehicleNumber": 2222
            }
        ]
        ];
        
        dataService.getData().subscribe((event: HttpEvent<any>) => {
          switch (event.type) {
            case HttpEventType.Response:
              expect(event.body).toEqual(mockUsers);
          }
        });
        
        const mockReq = httpMock.expectOne(dataService.apiUrl);
        
        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');
        
        mockReq.flush(mockUsers);
        
        httpMock.verify();
      }
    )
  ));


});
