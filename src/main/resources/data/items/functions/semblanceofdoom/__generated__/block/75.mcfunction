#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set TEMP s.damage 19
execute at @s rotated as @e[tag=spell,tag=reflector,distance=..3,limit=1,sort=nearest] positioned as @s run function items:semblanceofdoom/__generated__/block/76
execute at @s as @e[tag=reflector,tag=spell,distance=..3] at @s run function items:semblanceofdoom/__generated__/block/77