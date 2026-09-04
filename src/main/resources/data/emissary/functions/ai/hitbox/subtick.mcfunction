#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[nbt={HurtTime:10s}] unless score EmissaryInvulnerable em.value matches 1 run function emissary:ai/hitbox/__generated__/block/738
execute as @s[tag=phase1] run function emissary:ai/hitbox/__generated__/block/739
execute as @s[tag=phase2] run function emissary:ai/hitbox/__generated__/block/740
execute as @s[tag=phase3] run function emissary:ai/hitbox/__generated__/block/741
execute as @s[tag=phase4] run function emissary:ai/hitbox/__generated__/block/742