import axios from "axios";
import { useEffect, useState } from "react";

const  Report = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        loadData();
    }
    , []);

    const loadData = () => {
        axios.get("/api/tickets", {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("access_token")}`,
            },
        }).then((response) => {
            console.log(response.data);
            setEvents(response.data);
        })
        .catch((error) => {
            console.error("There was an error fetching the events!", error);
        });
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <h1 className="text-2xl font-bold mb-4">Report</h1>
            <div className="row">
                <div className="col-md-12 mb-4">
                    <table className="table table-bordered table-striped">
                        <thead>
                            <tr className="bg-gray-100">
                                <th className="py-2 px-4 border-b">Event Name</th>
                                <th className="py-2 px-4 border-b">Date</th>
                                <th className="py-2 px-4 border-b">User ID</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                events.map((event) => (
                                    <tr key={event.id}>
                                        <td className="py-2 px-4 border-b">{event.eventid}</td>
                                        <td className="py-2 px-4 border-b">{event.bookingTime}</td>
                                        <td className="py-2 px-4 border-b">{event.userid}</td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default Report;