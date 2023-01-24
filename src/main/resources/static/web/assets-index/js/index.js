const app = Vue.createApp({
  data() {
    return {
      clients: [],
      email: "",
      pwd: "",
      firstName: "",
      lastName: "",
      anio: ""
    };
  },
  created() {

    this.anio = new Date().getFullYear();
  },
  methods: {
    login() {
      axios.post('/api/login', "email=" + this.email + "&pwd=" + this.pwd, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } })
        .then((response) => (location.href = "/web/accounts.html"))
        .catch((response) =>
          Swal.fire({
            icon: "error",
            title: "Oops! some is wrong",
            text: "Please, check the fileds.",
          })
        );
    },

    register() {
      axios.post("/api/clients",
        "firstName=" + this.firstName + "&lastName=" + this.lastName + "&email=" + this.email + "&password=" + this.pwd,
        { headers: { "Content-Type": "application/x-www-form-urlencoded" } })
        .then((response) => this.login())
        .catch(function (error) {
          console.log(error);
        });
    },
  },
}).mount("#app");
