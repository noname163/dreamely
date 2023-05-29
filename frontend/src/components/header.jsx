import React, { Component } from 'react';

class Header extends Component {
    render() {
        return (
            <React.Fragment>
                <div id="demo" className="carousel slide d-block pt-5" data-ride="carousel" >
                    <ul className="carousel-indicators">
                        <li data-target="#demo" data-slide-to="0" className="active"></li>
                        <li data-target="#demo" data-slide-to="1"></li>
                        <li data-target="#demo" data-slide-to="2"></li>
                    </ul>
                    <div className="carousel-inner">
                        <div className="carousel-item active">
                            <img src="/images/banner1.jpg" alt="Los Angeles" width="1100" height="300" />
                            <div className="carousel-caption">
                                <h3>Flower Shop: A Blooming Delight</h3>
                                <p>Thank you for visiting us and enjoying our floral sight!</p>
                            </div>
                        </div>
                        <div className="carousel-item">
                            <img src="/images/banner2.jpg" alt="Chicago" width="1100" height="300" />
                            <div className="carousel-caption">
                                <h3>Follow the Flower Event</h3>
                                <p>Book us today and get a bouquet that will make your day!</p>
                            </div>
                        </div>
                        <div className="carousel-item">
                            <img src="/images/banner3.jpg" alt="New York" width="1100" height="300" />
                            <div className="carousel-caption">
                                <h3>Flowers for You, Service for Two</h3>
                                <p>We find the best flowers that suit your style and budget!</p>
                            </div>
                        </div>
                    </div>
                    <a className="carousel-control-prev" href="#demo" data-slide="prev">
                        <span className="carousel-control-prev-icon"></span>
                    </a>
                    <a className="carousel-control-next" href="#demo" data-slide="next">
                        <span className="carousel-control-next-icon"></span>
                    </a>
                </div>

            </React.Fragment>
        );
    }
}

export default Header;