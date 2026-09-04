#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s run tp @s ~ ~ ~ ~ ~
execute at @s run bossbar set minecraft:emissary players @a[distance=..300]
execute as @s[tag=backwardfly] at @s rotated ~ 35 run function emissary:__generated__/block/11
execute as @s[tag=backwardfly2] at @s rotated ~ 0 run function emissary:__generated__/block/15
execute as @s[tag=forwardfly] at @s rotated ~ 0 run function emissary:__generated__/block/16
execute as @s[tag=forwardslide] at @e[tag=emissary,tag=locator,tag=foot] positioned ~ ~0.2 ~ run function emissary:__generated__/block/17
execute if score Idle em.value matches 1 at @s run function emissary:__generated__/block/18
execute as @s[tag=strafingright2] at @s run function emissary:attacks/phase2/straferight/subtick
execute as @s[tag=strafingleft2] at @s run function emissary:attacks/phase2/strafeleft/subtick
execute if score Flying em.value matches 1 at @s run function emissary:ai/phase/4/fly
execute as @s[tag=flyup_godrays] run tp @s ~ ~1.5 ~
execute at @s unless entity @a[distance=..300] run function emissary:__generated__/block/19