import React, { useState } from "react"
import * as ReactDOM from "react-dom"
import { initializeIcons, Text } from "@fluentui/react"
import TBQL from "./components/tbql"
import ST2G from "./components/ST2G"

initializeIcons("https://static2.sharepointonline.com/files/fabric/assets/icons/")

const Index: React.FunctionComponent = () => {
    const [graphId, setGraphId] = useState(-1)
    const updateGraph = () => setGraphId(graphId + 1)

    return <>
        <Text as="p" className="title" variant="xxLarge">ThreatRaptor Web UI</Text>
        <ST2G updateGraph={updateGraph} />
        {graphId >= 0 && <TBQL graphId={graphId} />}
    </>
}

ReactDOM.render(
    <Index />, 
    document.getElementById("app")
)
