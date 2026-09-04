#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=eye] if score Emissary em.phase matches 4 at @s run function emissary:__generated__/block/4
execute as @s[tag=wing,tag=phase0] if score Emissary em.phase matches 1 at @s run function emissary:__generated__/block/5
execute as @s[tag=wing,tag=phase1] if score Emissary em.phase matches 2 at @s run function emissary:__generated__/block/6
execute as @s[tag=hand] if score Emissary em.phase matches 4 run function emissary:__generated__/block/7
execute as @s[tag=foot] if score Emissary em.phase matches 4 run function emissary:__generated__/block/8
execute as @s[tag=halo] if score Emissary em.phase matches 4 run function emissary:__generated__/block/9