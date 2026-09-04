#built using mc-build (https://github.com/mc-build/mc-build)

particle block ice ~ ~ ~ 0 0 0 0 1 force
particle electric_spark ~ ~ ~ 0 0 0 0 1 force
execute if entity @a[distance=..2,gamemode=!spectator] run tag @s add kill
execute as @a[distance=..2,tag=!bladehit] run function sorcerer:attacks/frozenblades/hit