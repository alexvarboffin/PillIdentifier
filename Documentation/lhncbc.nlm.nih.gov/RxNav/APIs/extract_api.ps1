$files = Get-ChildItem "api-*.html"
$results = @()

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $obj = @{
        SourceFile = $file.Name
        OperationId = ""
        Method = ""
        Path = ""
        Description = ""
        Parameters = @()
        Tag = ""
    }

    # Determine Tag
    if ($file.Name -like "api-RxNorm.*") { $obj.Tag = "RxNorm" }
    elseif ($file.Name -like "api-Prescribable.*") { $obj.Tag = "Prescribable" }
    elseif ($file.Name -like "api-RxTerms.*") { $obj.Tag = "RxTerms" }
    elseif ($file.Name -like "api-RxClass.*") { $obj.Tag = "RxClass" }

    # OperationId
    if ($content -match '<title>\s*(.*?)\s*-\s*.*API.*</title>') {
        $obj.OperationId = $matches[1].Trim()
    }

    # HTTP Request
    $idx = $content.IndexOf("HTTP request")
    if ($idx -ge 0) {
        $start = $idx
        $length = [Math]::Min(500, $content.Length - $start)
        $region = $content.Substring($start, $length)
        
        # Match Method (GET, POST, etc.)
        if ($region -match '(GET|POST|PUT|DELETE)') {
            $obj.Method = $matches[1]
            
            # Find path after method
            # We look for the method, then whitespace, then the path until <, space, or newline
            # Regex: Method followed by whitespace, then capture group (path)
            $methodPattern = [regex]::Escape($obj.Method)
            $pathPattern = "$methodPattern[\s\u00A0]+([^<\s]+)"
            
            if ($region -match $pathPattern) {
                $rawPath = $matches[1]
                # Remove .xml, .json extension if present
                $rawPath = $rawPath -replace '\.xml', ''
                $rawPath = $rawPath -replace '\.json', ''
                # Remove query params
                $rawPath = $rawPath -replace '\?.*', ''
                $obj.Path = $rawPath
            }
        }
    }

    # Description
    # Construct regex pattern separately to avoid syntax issues
    $descId = $obj.Tag + "." + $obj.OperationId + ".description"
    $descPattern = 'id="' + [regex]::Escape($descId) + '"><h4>Description</h4>(.*?)</div>'
    
    if ($content -match $descPattern) {
        $descBlock = $matches[1]
        # Extract first paragraph content
        if ($descBlock -match '<p>(.*?)</p>') {
            $descText = $matches[1]
            # Remove HTML tags
            $descText = [regex]::Replace($descText, '<[^>]+>', '')
            $obj.Description = $descText.Trim()
        }
    }

    # Parameters
    $paramMatches = [regex]::Matches($content, '<span class="rxapi-parameter">(.*?)</span>')
    $params = @()
    foreach ($match in $paramMatches) {
        $p = $match.Groups[1].Value.Trim()
        if ($p -notin $params) {
            $params += $p
        }
    }
    $obj.Parameters = $params

    $results += $obj
}

$results | ConvertTo-Json -Depth 4 > api_data.json
