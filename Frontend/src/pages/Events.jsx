import axios from "axios";
import { useEffect, useState } from "react";

const Events = () => {
    const [events,setevents]=useState([]);
    const [event,setevent]=useState({
        name: "",
        date: "",
        price: "",
        location: ""
    });
    const handleChange = (e) => {
        const { name, value } = e.target;
        setevent((prev) => ({
            ...prev,
            [name]: value,
        }));
    }       
    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(event);
        axios.post("http://localhost:8002/api/events", event, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("access_token")}`,
                },
            })
            .then((response) => {
                console.log(response.data);
                setevents((prev) => [...prev, response.data]);
                loadData(); // Reload the data after adding a new event
                setevent({
                    name: "",
                    date: "",
                    price: "",
                    location: ""
                });
                e.target.reset();
            })
            .catch((error) => {
                console.error("There was an error adding the event!", error);
            });
    }

    const loadData = () => {
        axios.get("http://localhost:8002/api/events", {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("access_token")}`,
            },
        })
        .then((response) => {
            console.log(response.data);
            setevents(response.data);
        })
        .catch((error) => {
            console.error("There was an error fetching the events!", error);
        });
    }
    

    useEffect(() => {
        loadData();
    }, []);
    return (
        <div className="container">
            <div className="row">
                <div className="col-md-8 mt-5">
                    <h4>Event List</h4>
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Event Name</th>
                                <th scope="col">Date</th>
                                <th scope="col">Price</th>
                                <th scope="col">Location</th>
                            </tr>
                        </thead>
                        <tbody>
                            {events.map((event) => (
                                <tr key={event.id}>
                                    <td>{event.id}</td>
                                    <td>{event.name}</td>
                                    <td>{event.date}</td>
                                    <td>{event.price}</td>
                                    <td>{event.location}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <div className="col-md-4 mt-5 text-start">
                    <h4>Add New Event</h4>
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label htmlFor="eventName" className="form-label">Event Name</label>
                            <input type="text" onChange={handleChange} name="name" className="form-control" id="eventName" placeholder="Enter event name" required />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventDate" className="form-label">Event Date</label>
                            <input type="date" onChange={handleChange} name="date" className="form-control" id="eventDate" required />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventPrice" className="form-label">Event Price</label>
                            <input type="number" onChange={handleChange} name="price" className="form-control" id="eventPrice" placeholder="Enter event price" required />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventLocation" className="form-label">Event Location</label>
                            <input type="text"  onChange={handleChange} name="location" className="form-control" id="eventLocation" placeholder="Enter event location" required />
                        </div>
                        <button type="submit" className="btn btn-primary">Add Event</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default Events;