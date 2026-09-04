#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s a.dashtimer
tag @s remove a.dash
scoreboard players set @s a.dashcooldown 10
effect clear @s speed
effect clear @s resistance
advancement revoke @s only artifacts:locomotion/dash
advancement revoke @s only artifacts:locomotion/enddash