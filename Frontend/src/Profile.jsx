import { useAuth0 } from "@auth0/auth0-react";
import { useEffect } from "react";

const Profile = () => {
    const { user, isAuthenticated, isLoading, getAccessTokenSilently} = useAuth0();
    console.log(user);
    useEffect(() => {
        const getAccessToken = async () => {
            const token = await getAccessTokenSilently();
            localStorage.setItem("access_token", token);
            localStorage.setItem("user_id", user.email);
        };
        getAccessToken();
    },[]);
    
    if (isLoading) {
      return <div>Loading ...</div>;
    }
    return (
        isAuthenticated && (
            <div>
              <h4>Role: {user['app/roles']!="admin" ? "USER":"ADMIN"}</h4>
              <h2>{user.name}</h2>
              <p>{user.email}</p>
            </div>
          )
    );
}

export default Profile;