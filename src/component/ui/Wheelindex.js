import { StrictMode } from "react";
import { createRoot } from "react-dom/client";

import Wheel from "./Wheel1";

const rootElement = document.getElementById("root");
const root = createRoot(rootElement);

root.render(<Wheel/>);
