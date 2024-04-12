<template>
  <div id="app">
    <router-link to='/'>
      <img src='@/assets/logo.png' alt="Logo" class="logo" />
    </router-link>
    <router-view>
      
    </router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
h1 {
    color: #333;
    text-decoration: underline;
    font-size: 48px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}
h2 {
    color: #333;
    font-size: 24px;
    font-weight: bold;
    text-align: left;
    margin: 20px 0;
    padding: 10px;
    background-color: #f2f2f2;
    border-bottom: 2px solid #4CAF50;
}
  span {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
  .rowName{
    margin-right: 300px;
  }
  th, td{
    padding-right:12px;
  }
  .error {
    color: #a94442; /* red color */
    background-color: #f2dede; /* light red background */
    border: 1px solid #ebccd1; /* red border */
    padding: 15px; /* space inside the box */
    margin-bottom: 20px; /* space below the box */
    border-radius: 5px; /* rounded corners */
    text-align: center; /* center the text */
}
.success{
    color: #3c763d; /* green color */
    background-color: #dff0d8; /* light green background */
    border: 1px solid #d6e9c6; /* green border */
    padding: 15px; /* space inside the box */
    margin-bottom: 20px; /* space below the box */
    border-radius: 5px; /* rounded corners */
    text-align: center; /* center the text */
}
  .center{
    margin-left: auto;
    margin-right: auto;
  }
  button.suggest {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}
input{
    width:15%;
    padding:3px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-bottom: 5px;
    margin-top: 5px;
}
input.suggest {
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-bottom: 10px;
}
button.promote {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}

button.promote:hover {
    background-color: #45a049;
    color: white;
}

button.confirm{
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
    background-color: #f29b05;
}
button.confirm:hover{
    background-color: #ff8001;

}
button.demote {
    background-color: #f44336; /* Red */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}
button.demote:hover {
    background-color: #da190b;
    color: white;
}
button.view{
    background-color: #4250be;
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    transition-duration: 0.4s;
}

button.view:hover {
    background-color: #27b1b8;
}

table {
    border-collapse: collapse;
    margin-bottom: 20px;
}

table, th, td {
    border: 1px solid #ddd;
}

th, td {
    text-align: center;
    padding: 8px;
}

th {
    background-color: #6b589c;
    color: white;
}

tr:nth-child(even) {
    background-color: #f2f2f2;
}
</style>
