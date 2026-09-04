#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation m.temp m.hypertrace.pid = @s m.hypertrace.pid
scoreboard players operation m.temp m.hypertrace.id = @s m.hypertrace.id
execute as @a if score @s m.hypertrace.pid = m.temp m.hypertrace.pid run tag @s add m.hypertraceMatched
execute as @e[tag=hypertrace] if score @s m.hypertrace.id = m.temp m.hypertrace.id run tag @s add m.hypertraceMatched
scoreboard players add @s m.hypertrace.timer 1
execute if score @s m.hypertrace.timer matches 400.. run function magic:hypertrace/remove
execute as @e[tag=hypertrace,tag=m.hypertraceMatched] at @s run function magic:__generated__/block/1
execute at @s facing entity @e[tag=hypertrace,tag=aj.ajhypertrace.root,tag=!m.hypertraceMatched,distance=..1,limit=1] feet run tp @s ^ ^ ^-0.2
execute at @s run tp @s ~ ~ ~ ~1 ~
tag @e remove m.hypertraceMatched