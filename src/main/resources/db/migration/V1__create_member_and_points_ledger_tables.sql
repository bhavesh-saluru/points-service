-- This script creates the foundational tables for the KudosPoints system.

-- The 'members' table stores information about each loyalty program member.
CREATE TABLE members (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- The 'points_ledger' is an immutable, append-only log of all transactions.
-- This provides a full audit trail for every point earned or redeemed.
CREATE TABLE points_ledger (
    id UUID PRIMARY KEY,
    member_id UUID NOT NULL,
    points INTEGER NOT NULL,
    type VARCHAR(50) NOT NULL,
    transaction_id VARCHAR(255) UNIQUE, -- Optional external ID to prevent duplicates
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_members
        FOREIGN KEY(member_id)
        REFERENCES members(id)
        ON DELETE CASCADE
);
