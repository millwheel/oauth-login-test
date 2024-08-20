import "./styles.css";
import Button from "./Button";

function App() {
  return (
    <div>
      <div className="header">
        <h2 className="intro">Welcome to Login test App</h2>
        <div>
          <Button text={"login"} className={"login_button"}></Button>
          <Button text={"join"} className={"join_button"}></Button>
        </div>
      </div>
      <div className="body">
        <h1>You should login</h1>
      </div>
    </div>
  );
}

export default App;
