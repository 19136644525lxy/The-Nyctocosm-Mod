#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score TEMP m.hypertrace.pid run scoreboard players get @s m.hypertrace.pid
execute as @e[tag=hypertrace,tag=interactor] if score @s m.hypertrace.pid = TEMP m.hypertrace.pid at @s run function magic:hypertrace/destroy