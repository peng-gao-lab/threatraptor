import React, { useState } from "react"
import { PrimaryButton, Stack, TextField } from "@fluentui/react"

type ST2GProps = {
    updateGraph: () => void
}

const ST2G: React.FunctionComponent<ST2GProps> = (props) => {
    const [description, setDescription] = useState("First, /usr/bin/wget will be started by /bin/bash . It downloads some data from 162.125.6.6, then writes the data to sysrep_exp_data.txt.")
    const [svg, setSvg] = useState(null as string)
    const [fetching, setFetching] = useState(false)

    const generateGraph = async () => {
        setFetching(true)
        const response = await (await fetch("/st2g?des=" + encodeURIComponent(description))).json()
        if (response.err) {
            console.log(response.err)
        } else {
            const encoded = btoa(unescape(encodeURIComponent(response[".svg"])))
            setSvg("data:image/svg+xml;base64," + encoded)
            props.updateGraph()
        }
        setFetching(false)
    }

    return <Stack id="st2g" horizontalAlign="stretch" tokens={{childrenGap: 16}}>
        <TextField multiline rows={3}
            label="Description"
            value={description} 
            onChange={(_, v) => setDescription(v)} />
        <PrimaryButton
            disabled={description.trim().length === 0 || fetching}
            text={fetching ? "Generating graph..." : "Generate graph"}
            onClick={generateGraph} />
        {svg && <img className="svg" src={svg} />}
    </Stack>
}

export default ST2G
