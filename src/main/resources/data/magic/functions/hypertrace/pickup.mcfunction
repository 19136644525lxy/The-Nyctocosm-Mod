#built using mc-build (https://github.com/mc-build/mc-build)

tag @e remove m.hypertraceMatched
scoreboard players operation m.temp m.hypertrace.pid = @s m.hypertrace.pid
scoreboard players operation m.temp m.hypertrace.id = @s m.hypertrace.id
execute as @a if score @s m.hypertrace.pid = m.temp m.hypertrace.pid run tag @s add m.hypertraceMatched
execute as @e[tag=hypertrace] if score @s m.hypertrace.id = m.temp m.hypertrace.id run tag @s add m.hypertraceMatched
scoreboard players add @a[tag=m.hypertraceMatched,limit=1] m.hypertrace.charge 1
execute as @e[tag=hypertrace,tag=m.hypertraceMatched,tag=aj.ajhypertrace.root] run function magic:hypertrace/remove
execute at @s run playsound nyctocosm:magic.hypertrace.pickup master @a[distance=..20] ~ ~ ~ 1