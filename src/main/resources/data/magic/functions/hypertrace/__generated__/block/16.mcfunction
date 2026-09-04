#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s m.hypertrace.pid = m.global m.hypertrace.pid
scoreboard players operation @s m.hypertrace.id = m.global m.hypertrace.id
tag @s add hypertrace
tag @s add interactor
data merge entity @s {width:0.7f,height:0.7f,response:1b}