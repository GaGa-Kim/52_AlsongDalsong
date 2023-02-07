import Sortbutton from "./SortButton";
import "./selectdrop.css";
import Button from "./Dropbutton";
import { useNavigate } from "react-router-dom";
import React, { useState, useEffect, useRef } from "react";

function SelectDrop() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const navigateHome = () => {
    // 👇️ navigate to /
    navigate("/");
  };

  let menuRef = useRef();

  useEffect(() => {
    let handler = (e) => {
      if (!menuRef.current.contains(e.target)) {
        setOpen(false);
        console.log(menuRef.current);
      }
    };

    document.addEventListener("mousedown", handler);

    return () => {
      document.removeEventListener("mousedown", handler);
    };
  });

  return (
    <div className="App">
      <div className="menu-container" ref={menuRef}>
        <div
          className="menu-trigger"
          onClick={() => {
            setOpen(!open);
          }}
        >
          <Sortbutton title="최신글/인기글" />
        </div>

        <div className={`dropdown-menu ${open ? "active" : "inactive"}`}>
          <ul>
            <Button title="최신글" onClick={navigateHome} />
            <Button title="인기글" onClick={navigateHome} />
          </ul>
        </div>
      </div>
    </div>
  );
}
export default SelectDrop;
