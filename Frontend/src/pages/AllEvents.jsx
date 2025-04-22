import axios from "axios";
import { useEffect, useState } from "react";

const AllEvents = () => {
    const [events,setevents]=useState([]);
    const loadData = () => {
        axios.get("/api/events", {
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

    const bookTicket = (eventId) => {
        axios.post(`/api/tickets`, {
            userid: localStorage.getItem("user_id"),
            eventid: eventId,
            bookingTime: new Date().toISOString(),
        }, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("access_token")}`,
            },
        })
        .then((response) => {
            console.log(response.data);
            alert("Ticket booked successfully!");
        })
        .catch((error) => {
            console.error("There was an error booking the ticket!", error);
            alert("Failed to book ticket. Please try again.");
        });
    }
    

    useEffect(() => {
        loadData();
    }, []);

    return (
        <div className="container mx-auto px-4 py-8">
            <h1 className="text-2xl font-bold mb-4">All Events</h1>
            <div className="row">
                <div className="col-md-12 mb-4">
                <table className="table table-bordered table-striped">
                    <thead>
                        <tr className="bg-gray-100">
                            <th className="py-2 px-4 border-b">Event Name</th>
                            <th className="py-2 px-4 border-b">Date</th>
                            <th className="py-2 px-4 border-b">Price</th>
                            <th className="py-2 px-4 border-b">Location</th>
                            <th className="py-2 px-4 border-b">Actions</th>
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
                                    <td><button className="btn btn-primary btn-sm" onClick={e=>bookTicket(event.id)}>Book Now</button></td>
                                </tr>
                            ))}
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    )
}

export default AllEvents;