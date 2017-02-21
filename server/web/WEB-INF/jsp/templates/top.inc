<html>
<head>
    <style>

        .heading {
            text-align: center;
            margin-left: auto;
            margin-right: auto;
            padding:0px;
            border: 0px;
            background-color: deepskyblue;
        }

        .header {
            color: aqua;
            font-size: 40px;
        }

        .main_body {
            padding: 10;
            align-content: center;
            background-color:floralwhite;
        }

        .page_layout {
            margin-top:-30;
            border: 0px;
            table-layout:fixed;
        }

        .main_options {
            background-color:dodgerblue;
            font-size: 23px;
            font-family: serif;
            width: 100vw;
        }

        .options {
            clear: both;
            height:0px;
            padding:0px;


        }

        .sub_options {
            background-color: aqua;
        }

        .Footer {
            width:100%;
            background-color:cornflowerblue
        }

        .content {
            height:250;
        }




    </style>
    <title>
        Metabolic App Web Interface
    </title>
</head>
<body class="main_body">

<div class="heading">
    <h1 class="header">
        Metabolic App Web Interface(WireFrame)
    </h1>
</div>


<table class="page_layout">
    <tr style="width:100%">
        <th class="main_options">

            <a href="/logout">Logout</a>
            <a href="/home">Home</a>
        </th>
    </tr>
</table>


<table class="page_layout" style="margin-top:-2;">

    <tr class="sub_options">
        <th class="options">
            Clinician
        </th>
        <th class="options">
            Patient(Debug)
        </th>
    </tr>
</table>

<table style="width:100%;margin-top:-20;">
    <tr>
        <td class="content">