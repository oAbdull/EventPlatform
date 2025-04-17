import { useAuth0 } from "@auth0/auth0-react";

const Login = () => {
    const { loginWithRedirect } = useAuth0();

  return (
      <div className="container">
        <h2>Welcome to Event Management</h2>
        <h4>Login Screen</h4>
        <button className="btn btn-primary mt-5" onClick={() => loginWithRedirect()}>Log In</button>
      </div>
  );
}

export default Login;