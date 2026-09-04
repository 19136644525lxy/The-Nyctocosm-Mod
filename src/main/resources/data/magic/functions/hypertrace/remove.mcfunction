#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation m.temp m.hypertrace.id = @s m.hypertrace.id
execute as @e[tag=hypertrace] if score @s m.hypertrace.id = m.temp m.hypertrace.id run tag @s add m.hypertraceMatched
function animated_java:ajhypertrace/remove/this
kill @e[tag=hypertrace,tag=m.hypertraceMatched]
tag @e remove m.hypertraceMatched