import React, { useState } from "react"
import { DefaultButton, DetailsList, DetailsListLayoutMode, IColumn, MessageBar, 
    MessageBarType, PrimaryButton, SelectionMode, Stack, TextField } from "@fluentui/react"

type TBQLProps = {
    graphId: number
}

const TBQL: React.FunctionComponent<TBQLProps> = (props) => {
    const [loaded, setLoaded] = useState(-1)
    const [loading, setLoading] = useState(false)
    const [tbql, setTbql] = useState("")
    const [executing, setExecuting] = useState(false)
    const [info, setInfo] = useState("")
    const [rows, setRows] = useState(null as any)
    const [cols, setCols] = useState(new Array<IColumn>())

    const generateTBQL = async () => {
        setLoading(true)
        const response = await (await fetch("/tbql")).json()
        if (response.err) {
            console.log(response.err)
        } else {
            setTbql(response.tbql)
            setLoaded(props.graphId)
        }
        setInfo("")
        setRows(null)
        setLoading(false)
    }

    const executeTBQL = async () => {
        setExecuting(true)
        const response = await (await fetch("/execute?tbql=" + encodeURIComponent(tbql))).json()
        if (response.err) {
            setInfo(response.err)
            setRows(null)
        } else {
            const lengths: number[] = response.headers.map(() => 0)
            setRows(response.rows.map((s: string) => {
                const fields = s.split(",")
                const row: any = {}
                for (let i = 0; i < response.headers.length; i += 1) {
                    row[response.headers[i]] = fields[i]
                    lengths[i] += fields[i].length
                }
                return row
            }))
            const totalLength = lengths.reduce((a, b) => a + b, 0)
            setCols(response.headers.map((s: string, i): IColumn => ({
                key: String(i),
                name: s,
                minWidth: 50,
                maxWidth: 900 * lengths[i] / totalLength,
                fieldName: s,
                isMultiline: true,
                isResizable: true,
                isCollapsible: true,
            })))
            setInfo(`Successfully loaded ${response.rows.length} row(s) in ${response.executionTime} ms.`)
        }
        setExecuting(false)
    }

    return <Stack horizontalAlign="stretch" tokens={{childrenGap: 16}}>
        {loaded < props.graphId
        ?   <DefaultButton
                disabled={loading}
                text="Generate query"
                onClick={generateTBQL} />
        : <>
            <TextField multiline rows={5}
                label="Query"
                value={tbql}
                spellCheck={false}
                onChange={(_, v) => setTbql(v)} />
            <PrimaryButton 
                disabled={tbql.trim().length === 0 || executing}
                text={executing ? "Executing query..." : "Execute query"}
                onClick={executeTBQL} />
            {!executing && info && <MessageBar
                messageBarType={rows ? MessageBarType.info : MessageBarType.warning}
                isMultiline={false}>
                {info}
            </MessageBar> }
            {!executing && rows && <DetailsList
                items={rows}
                columns={cols}
                selectionMode={SelectionMode.none}
                layoutMode={DetailsListLayoutMode.justified} />}
        </>}
    </Stack>
}

export default TBQL
