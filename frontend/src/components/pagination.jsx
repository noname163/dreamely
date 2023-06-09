import React, { Component } from 'react';
import _ from 'lodash';

const Pagination = props =>{
    const {itemsCount, pageSize, currentPage,onPageChange, btn} = props;
    const pagesCount = Math.ceil(itemsCount/pageSize);
    if(pagesCount===1) return null;
    var pages=[];
    if(pagesCount>10){
        if(currentPage==1){
            pages = _.range(1,11);
        }
        else if(currentPage!=1 && currentPage+10<pagesCount+1){
            pages = _.range(currentPage,currentPage+11);
        }
        else if(pagesCount+1-11==currentPage){
            pages = _.range(currentPage,pagesCount+1);
        }
        else if(currentPage+11>pagesCount && currentPage<=pagesCount){
            pages = _.range(pagesCount-10,pagesCount+1);
        }
    }
    
    
    return (
        <nav aria-label="Page navigation example">
            <ul className="pagination">
                <li className="page-item">
                    <a className="page-link" onClick={() => btn("pre")}>Previous</a>
                </li>
                {
                    pages.map(page=>(
                        <li className={page==currentPage ?"page-item active" :"page-item"} key={page}>
                            <a className="page-link" onClick={() => onPageChange(page)}>{page}</a>
                        </li>
                    ))
                }
                <li className="page-item">
                    <a className="page-link" onClick={() => btn("next")} >Next</a>
                </li>
            </ul>
        </nav>
    );
}

// Pagination.PropTypes={
//     itemsCount: PropTypes.number.isRequired, 
//     pageSize: PropTypes.number.isRequired, 
//     currentPage: PropTypes.number.isRequired,
//     onPageChange: PropTypes.func.isRequired, 
//     btn:PropTypes.func.isRequired
// }

export default Pagination;