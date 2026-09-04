#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add hittest
execute at @s positioned ~-0.3 ~ ~-0.3 as @e[dx=0,dy=0.8,dz=0,tag=hitregion,tag=hittest] positioned ~-0.4 ~ ~-0.4 as @s[dx=0,dy=0.8,dz=0] run tag @a[tag=hittest] add hit
tag @s remove hittest
execute as @s[tag=hit] run function emissary:attacks/phase3/cross/__generated__/block/495
tag @s remove hit