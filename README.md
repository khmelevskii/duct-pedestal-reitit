# duct-pedestal-reitit

Simple example of using Duct, Pedestal and Reitit together.

Also was added:
- Example of coersion based on clojure.spec
- Handle of validation problems
- Migrations based on ragtime

TODO:
- Create backend and frontend monorepo
- Use shared routes with coersions on frontend (inside re-frame effects) and backend
- Generate swagger
- Add tests

*Please pay attention*
`duct.module/sql` was added and configured with using `postgres`. So you should run postgres database or comment `duct.module/sql` parts.

## Developing

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

Only `/tasks` route was added with `archived` query param. So, only `/tasks?archived=true` and `/tasks?archived=false` are available.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```

## Legal

Copyright © 2019 FIXME
