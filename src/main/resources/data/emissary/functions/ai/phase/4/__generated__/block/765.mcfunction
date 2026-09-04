#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ 0
execute as @s[tag=corkscrewing] run function emissary:ai/phase/4/flytarget
execute as @s[tag=corkscrewing] run tag @s remove quickturn
execute at @s[tag=grapplegrabbed] run function emissary:ai/phase/4/__generated__/block/766