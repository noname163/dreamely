import React, { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import accountService from '../service/accountService';
import * as authenService from '../service/authenService';

export default function EditInformation() {

    const [data, setData] = useState({});

    const getData = async () => {
        const response = await authenService.getUserInformation();
        setData(response.data);
    }
    useEffect(() => {
        getData();

    }, []);

    const handleChange = (e) => {
        setData({
            ...data,
            [e.target.name]: e.target.value,
        })
    };
    const submitProduct = async () => {
        try {
            await accountService.updateAccount(data);
            toast.success('Edit Information Success.');
            window.location = "/account-admin";
        } catch (ex) {
            toast.error(ex.response.data.mess)
            if (ex.response && ex.response.status === 400) {
                toast.error(ex)
            }
        }
    }
    return (
        <React.Fragment>
            <ToastContainer />
            <div className="container tm-mt-big tm-mb-big">
                <div className="row">
                    <div className="col-xl-9 col-lg-10 col-md-12 col-sm-12 mx-auto" style={{ marginLeft: 'auto!important' }}>
                        <div className="tm-bg-primary-dark tm-block tm-block-h-auto" style={{ backgroundColor: '#435c70', minHeight: '1px', maxHeight: 'none', height: 'auto', padding: '40px' }}>
                            <div className="row" style={{ display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' }}>
                                <div className="col-12" style={{ flex: '0 0 100%', maxWidth: '100%' }}>
                                    <h2 className="tm-block-title d-inline-block" style={{ fontSize: '1.1rem', fontWeight: '700', color: '#fff', marginBottom: '30px' }}>Edit Information</h2>
                                </div>
                            </div>
                            <div className="row tm-edit-product-row" style={{ display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' }}>
                                <div className="col-xl-6 col-lg-6 col-md-12" style={{ flex: '0 0 100%', maxWidth: '100%' }}>
                                    <form action="" className="tm-edit-product-form" style={{ display: 'block', marginTop: '0em' }}>
                                        <div className="form-group mb-3" style={{ marginBottom: '1rem !important' }}>
                                            <label htmlFor="fullName">Full name
                                            </label>
                                            <input
                                                id="fullName"
                                                name="fullName"
                                                type="text"
                                                value={data?.fullName}
                                                className="form-control validate"
                                                onChange={handleChange}
                                                required
                                            />

                                        </div>
                                        <div className="form-group mb-3" style={{ marginBottom: '1rem !important' }}>
                                            <label
                                                htmlFor="description">
                                                Description
                                            </label>
                                            <textarea
                                                className="form-control validate"
                                                rows="3"
                                                name='description'
                                                value={data?.description}
                                                onChange={handleChange}
                                                required
                                            ></textarea>
                                        </div>
                                        <div className="row" style={{ display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' }}>
                                            <div className="form-group mb-3 col-xs-12 col-sm-6">
                                                <label
                                                    htmlFor="phoneNumber">Phone
                                                </label>
                                                <input
                                                     id="phoneNumber"
                                                     name="phoneNumber"
                                                     type="text"
                                                     value={data?.phoneNumber}
                                                     className="form-control validate"
                                                     onChange={handleChange}
                                                     required
                                                />
                                            </div>
                                            <div className="form-group mb-3 col-xs-12 col-sm-6">
                                                <label
                                                    htmlFor="email"
                                                >Email
                                                </label>
                                                <input
                                                    disabled
                                                    id="email"
                                                    name="email"
                                                    value={data?.email}
                                                    type="text"
                                                    className="form-control validate"
                                                    required
                                                />
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div className="col-12" >
                                    <input style={{ color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' }}
                                        type="button"
                                        id="uploadBtn"
                                        onClick={submitProduct}
                                        className="btn btn-primary btn-block mx-auto"
                                        value="UPDATE ACCOUNT INFORMATION"
                                    />
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </React.Fragment>
    )
}
