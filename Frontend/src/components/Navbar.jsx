import { useAuth0 } from "@auth0/auth0-react";
import { Link } from "react-router-dom";

const Navbar = () => {
    const { user, isAuthenticated,logout } = useAuth0();

    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
            <div className="container-fluid">
                <Link className="navbar-brand" to="#">Event Management</Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link active" aria-current="page" to="/profile">Profile</Link>
                        </li>
                        {isAuthenticated && user['app/roles'] == "ADMIN" ? (
                            <>
                            <li className="nav-item">
                                <Link className="nav-link" to="/events">Events</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link">Reports</Link>
                            </li>
                            <li className="nav-item">
                            <Link className="nav-link" to="/bookings">Bookings</Link>
                        </li>
                        </>
                        ):(
                            <>
                        <li className="nav-item">
                            <Link className="nav-link active" aria-current="page" to="/allevents">Events</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/bookings">My Bookings</Link>
                        </li>
                        </>
                        )}
                        
                        
                        <li className="nav-item">
                            <Link className="nav-link" to="#" onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>Logout</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default Navbar;