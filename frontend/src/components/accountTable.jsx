import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import swal from "sweetalert";
import Joi from 'joi-browser';
import http from "../service/httpService";
import productService from '../service/productService.js';
import * as authenService from '../service/authenService';

const ProductTable = () => {
  const [posts, setPosts] = useState();
  const [category, setCategory] = useState({
    name: "",
    description: ""
  });
  const [errors, setErrors] = useState({});
  const [reload, setReload] = useState(false); // New state variable for disabled products
  const [sort, setSort] = useState({ column: "name", direction: "asc" }); // New state for sorting

  const schema = {
    name: Joi.string()
      .required()
      .label("Name"),
    description: Joi.string()
      .required()
      .label("Description")
  };

  const validate = () => {
    const options = { abortEarly: false };
    const { error } = Joi.validate(category, schema, options);
    if (!error) return null;

    const errors = {};
    for (let item of error.details) errors[item.path[0]] = item.message;
    return errors;
  };

  const validateProperty = ({ name, value }) => {
    const obj = { [name]: value };
    const schema = { [name]: schema[name] };
    const { error } = Joi.validate(obj, schema);
    return error ? error.details[0].message : null;
  };

  const fetchData = async () => {
    const response = await authenService.getUserInformation();
    setPosts(response.data);
  };


  useEffect(() => {
    fetchData();
  }, [reload]);

  const onConfirm = (id) => {
    swal({
      title: "Are you sure?",
      text: "Once deleted, you will not be able to recover this product again!",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    }).then((willDelete) => {
      if (willDelete) {
        const accessToken = localStorage.getItem("Access-Token");
        const options = {
          headers: {
            Authorization: "Bearer " + accessToken
          }
        };
        swal("Poof! Product has been deleted!", {
          icon: "success",
        });
        http.delete("http://localhost:8080/admin/product/" + id, options)
          .then(() => {
            setReload(!reload); 
          })
          .catch((error) => {
            toast.error(error)
          });
      } else {
        swal("Your imaginary file is safe!");
      }
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const errors = validate();
    setErrors(errors || {});
    if (errors) return;
    newCategory();
  };

  const newCategory = async () => {
    try {
      await productService.newCategory(category);
      toast.success('Add New Category Success.');
      window.location = "/product-admin";
    } catch (ex) {
      const errors = { ...errors };
      toast.error(ex.response.data.message);
      setErrors(errors);
    }
  };

  const handleChange = ({ currentTarget: input }) => {
    const errors = { ...errors };
    const errorMessage = validateProperty(input);
    if (errorMessage) errors[input.name] = errorMessage;
    else delete errors[input.name];

    const updatedCategory = { ...category };
    updatedCategory[input.name] = input.value;
    setCategory(updatedCategory);
    setErrors(errors);
  };
 

  return (
    <React.Fragment>
      <ToastContainer />
      <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col">#</th>
            <th scope="col">
              Full Name
            </th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th scope="col">Description</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
        <tr >
              <th scope="row">1</th>
              <td>{posts?.fullName}</td>
              <td>{posts?.email}</td>
              <td>{posts?.phoneNumber}</td>
              <td>{posts?.description}</td>
              <td>
                <div className="btn-group" role="group" aria-label="Basic example">
                  <Link to="/edit-account" state={posts} className="btn btn-primary">Edit</Link>
                  
                </div>
              </td>
            </tr>
        </tbody>
      </table>

      <div className="modal fade" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div className="modal-dialog" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">New Category</h5>
              <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div className="modal-body">
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="category-name" className="col-form-label">Category Name:</label>
                  <input type="text" name="name" className="form-control" id="category-name" onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label htmlFor="description" className="col-form-label">Description:</label>
                  <textarea className="form-control" name="description" id="description" onChange={handleChange}></textarea>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                  <button type="submit" className="btn btn-primary">Save changes</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};

export default ProductTable;
