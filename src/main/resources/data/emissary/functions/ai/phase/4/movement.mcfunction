#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s facing entity @e[tag=emissary,tag=flytarget] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^25 facing entity @s feet facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s[tag=quickturn] facing entity @e[tag=emissary,tag=flytarget] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^10 facing entity @s feet facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^0.5
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough positioned over world_surface run function emissary:ai/phase/4/__generated__/block/765
execute at @s if entity @e[tag=emissary,tag=flytarget,distance=..5] run function emissary:ai/phase/4/__generated__/block/767
execute as @s[tag=corkscrewing] at @s run function emissary:ai/phase/4/__generated__/block/768
execute as @s[tag=grappletry] at @s run function emissary:ai/phase/4/__generated__/block/776
execute as @s[tag=grapplegrabbed] at @s run function emissary:ai/phase/4/__generated__/block/778
execute at @s positioned ^ ^0.5 ^ run function emissary:ai/phase/4/__generated__/block/786