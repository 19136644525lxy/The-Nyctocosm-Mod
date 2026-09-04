#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s a.hypertrace.timer
scoreboard players remove @s m.hypertrace.charge 1
execute if score @s m.hypertrace.charge matches ..0 run scoreboard players set @s m.hypertrace.charge 0