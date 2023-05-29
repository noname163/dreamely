import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../CSS/productCard.css";
import { paginate, previousAndNextBtn } from '../JS/paginate';
import config from "../config.json";
import http from "../service/httpService";
import Pagination from './pagination';

const ProductCard = () => {
    const [posts, setPosts] = useState([]);
    const [pageSize, setPageSize] = useState(3);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        fetchPosts();
    }, []);

    const fetchPosts = async () => {
        try {
            const { data } = await http.get(config.apiEndpoint + "/products");
            setPosts(data);
        } catch (error) {
            console.error("Error fetching posts:", error);
        }
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    const handleBtn = (st) => {
        setCurrentPage(previousAndNextBtn(posts.length, pageSize, currentPage, st));
    };

    const productData = paginate(posts, currentPage, pageSize);
    return (
        <React.Fragment>
            <ToastContainer />
            <div className="container mt-100">
                <div className="row">
                    {productData.map((item) => (
                        <div className="col-lg-4 col-md-6 col-sm-12" key={item.id}>
                            <div className="card mb-30">
                                <div className="inner card-img-tiles" style={{ width: '21rem', height: '15rem' }}>
                                    <div className="main-img" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%', height: '100%' }}>
                                        <img
                                            src={item.image}
                                            alt="Category"
                                            className="img-fluid"
                                            style={{ maxWidth: '100%', maxHeight: '100%', objectFit: 'cover' }}
                                        />
                                    </div>
                                </div>

                                <div className="card-body text-center">
                                    <h4 className="card-title">{item.name}</h4>
                                    <p className="text-muted">
                                        <span>{item.categories}</span> {item.price} VND
                                    </p>
                                    <Link className="btn btn-outline-primary btn-sm" to={'/product-detail'} state={item} data-abc="true" >
                                        View Products
                                    </Link>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <Pagination
                itemsCount={posts.length}
                pageSize={pageSize}
                currentPage={currentPage}
                onPageChange={handlePageChange}
                btn={handleBtn}
            />
        </React.Fragment>
    );
};

export default ProductCard;
