import alsong from "../ui/alsong.png";
import './Drop.css';

import React, {useState, useEffect, useRef} from 'react';

function Drop() {

  const [open, setOpen] = useState(false);

  let menuRef = useRef();

  useEffect(() => {
    let handler = (e)=>{
      if(!menuRef.current.contains(e.target)){
        setOpen(false);
        console.log(menuRef.current);
      }      
    };

    document.addEventListener("mousedown", handler);
    

    return() =>{
      document.removeEventListener("mousedown", handler);
    }

  });

  return (
    <div className="App">
      <div className='menu-container' ref={menuRef}>
        <div className='menu-trigger' onClick={()=>{setOpen(!open)}}>
          <img src={alsong}></img>
        </div>

        <div className={`dropdown-menu ${open? 'active' : 'inactive'}`} >
          <ul>
            <DropdownItem  text = {"My Profile"}/>
            <DropdownItem  text = {"Edit Profile"}/>
            <DropdownItem text = {"Inbox"}/>
            <DropdownItem  text = {"Settings"}/>
            <DropdownItem text = {"Helps"}/>
            <DropdownItem  text = {"Logout"}/>
          </ul>
        </div>
      </div>
    </div>
  );
}

function DropdownItem(props){
  return(
    <li className = 'dropdownItem'>
      <img src={props.img}></img>
      <a> {props.text} </a>
    </li>
  );
}

export default Drop;