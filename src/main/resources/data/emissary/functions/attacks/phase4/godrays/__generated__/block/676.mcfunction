#built using mc-build (https://github.com/mc-build/mc-build)

particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
particle flame ~ ~ ~ 0.2 0.2 0.2 0.01 2 force
particle wax_on ~ ~ ~ 0.2 0.2 0.2 0.01 2 force
scoreboard players reset TEMP em.value
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function emissary:attacks/phase4/godrays/__generated__/block/677
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase4/godrays/__generated__/block/678
execute as @s[tag=!reflected] positioned ^ ^ ^1 run function emissary:attacks/phase4/godrays/beam
execute as @s[tag=reflected] rotated as @e[tag=spell,tag=reflector,distance=..3] positioned ^ ^ ^1 run function emissary:attacks/phase4/godrays/beam