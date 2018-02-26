import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Randonneur } from './randonneur.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Randonneur>;

@Injectable()
export class RandonneurService {

    private resourceUrl =  SERVER_API_URL + 'api/randonneurs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(randonneur: Randonneur): Observable<EntityResponseType> {
        const copy = this.convert(randonneur);
        return this.http.post<Randonneur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(randonneur: Randonneur): Observable<EntityResponseType> {
        const copy = this.convert(randonneur);
        return this.http.put<Randonneur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Randonneur>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Randonneur[]>> {
        const options = createRequestOption(req);
        return this.http.get<Randonneur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Randonneur[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Randonneur = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Randonneur[]>): HttpResponse<Randonneur[]> {
        const jsonResponse: Randonneur[] = res.body;
        const body: Randonneur[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Randonneur.
     */
    private convertItemFromServer(randonneur: Randonneur): Randonneur {
        const copy: Randonneur = Object.assign({}, randonneur);
        copy.dateDeNaissance = this.dateUtils
            .convertDateTimeFromServer(randonneur.dateDeNaissance);
        return copy;
    }

    /**
     * Convert a Randonneur to a JSON which can be sent to the server.
     */
    private convert(randonneur: Randonneur): Randonneur {
        const copy: Randonneur = Object.assign({}, randonneur);

        copy.dateDeNaissance = this.dateUtils.toDate(randonneur.dateDeNaissance);
        return copy;
    }
}
