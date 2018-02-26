import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Randonnee } from './randonnee.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Randonnee>;

@Injectable()
export class RandonneeService {

    private resourceUrl =  SERVER_API_URL + 'api/randonnees';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(randonnee: Randonnee): Observable<EntityResponseType> {
        const copy = this.convert(randonnee);
        return this.http.post<Randonnee>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(randonnee: Randonnee): Observable<EntityResponseType> {
        const copy = this.convert(randonnee);
        return this.http.put<Randonnee>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Randonnee>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Randonnee[]>> {
        const options = createRequestOption(req);
        return this.http.get<Randonnee[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Randonnee[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Randonnee = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Randonnee[]>): HttpResponse<Randonnee[]> {
        const jsonResponse: Randonnee[] = res.body;
        const body: Randonnee[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Randonnee.
     */
    private convertItemFromServer(randonnee: Randonnee): Randonnee {
        const copy: Randonnee = Object.assign({}, randonnee);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(randonnee.date);
        return copy;
    }

    /**
     * Convert a Randonnee to a JSON which can be sent to the server.
     */
    private convert(randonnee: Randonnee): Randonnee {
        const copy: Randonnee = Object.assign({}, randonnee);

        copy.date = this.dateUtils.toDate(randonnee.date);
        return copy;
    }
}
